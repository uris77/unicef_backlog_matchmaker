package org.unicef.backlog.matchmaker.repository

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import org.unicef.backlog.matchmaker.dao.*
import org.unicef.backlog.matchmaker.domain.*

class FirebaseRepo {
    private var db: Firestore
    private var orgs: MutableSet<OrganizationDao> = mutableSetOf()
    private var projects: MutableSet<ProjectDao> = mutableSetOf()
    var volunteers: MutableSet<Volunteer> = mutableSetOf()
    var volunteerSkills: MutableSet<VolunteerSkill> = mutableSetOf()

    // Keeping it simple for POC and using only one volunteer.
    private val volunteerEmail = "volunteer1@email.com"

    init {
        // Initialize Firebase
        val firebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

        FirebaseApp.initializeApp(firebaseOptions)
        db = FirestoreClient.getFirestore()
    }

    fun fetchOrganizations(): List<Organization> {
        val orgRef = db.collection("organizations").get()
        orgs.clear()
        return orgRef.get().documents.map{doc ->
            val org = doc.toObject(OrganizationDao::class.java)
            org.id = doc.id
            orgs.add(org)
            Organization(name=org.name, repository = org.repository, website=org.website)
        }
    }

    fun fetchProjects(): List<Project> {
        val projectRef = db.collection("projects").get()
        projects.clear()
        return projectRef.get().documents.map{ doc ->
            val project = doc.toObject(ProjectDao::class.java)
            project.id = doc.id
            val org = orgs.find{o -> o.id == project.organization.trim()}
            if(org != null) {
                projects.add(project)
                Project(
                    name = project.name,
                    organization = Organization(
                        name = org.name, repository = org.repository, website = org.website
                    ))
            }
            else {
                Project(name="", organization=Organization(name="", repository = "", website = ""))
            }
        }
    }

    fun fetchProjectSkills(): List<ProjectSkill> {
        val ref = db.collection("projectSkills").get()
        return ref.get().documents.map{doc ->
            val skill = doc.toObject(ProjectSkillDao::class.java)
            val project = projects.find{p -> p.id == skill.project.trim()}
            val organization = orgs.find{ o -> o.id == project?.organization?.trim() }
            if (project != null && organization != null){
                ProjectSkill(
                    skill = skill.skill,
                    project = Project(name = project.name, organization = Organization(
                        name = organization.name,
                        website = organization.website,
                        repository = organization.repository
                    )))
            } else {
                ProjectSkill(skill = "", project = Project(name="", organization = Organization()))
            }
        }
    }

    fun fetchVolunteers(): List<Volunteer> {
        val ref = db.collection("volunteers").get()
        volunteers.clear()
        volunteerSkills.clear()
        return ref.get().documents.map{doc ->
            val volunteerDao = doc.toObject(VolunteerDao::class.java)
            val volunteer = Volunteer(name = volunteerDao.name, email = volunteerDao.email)
            volunteers.add(volunteer)

            val skills = volunteerDao.skills.map{skill ->
                VolunteerSkill(volunteer = volunteer, skill = skill)
            }
            volunteerSkills.addAll(skills)
            volunteer
        }
    }

    fun dataInput(): InitializeDataInput {
        val organizations = fetchOrganizations()
        val projs = fetchProjects()
        val projectSkills = fetchProjectSkills()
        fetchVolunteers()

        return InitializeDataInput(
            organizations = organizations,
            projects = projs,
            projectSkills = projectSkills,
            volunteers = volunteers.toList(),
            volunteerSkills = volunteerSkills.toList()
        )
    }

    fun saveVolunteerSkills(skills: List<String>) {
        val ref = db.collection("volunteers")
        val write = ref.document(volunteerEmail).update("skills", skills)
        write.get()
    }

    fun saveMatches(matched: List<VolunteerProjectMatch>) {
        val ref = db.collection("volunteers")
        val matchedProjects = matched.map{ m ->
            m.project
        }

        val write = ref.document(volunteerEmail).update("projects", matchedProjects)
        write.get()
    }

    fun fetchVolunteer(): VolunteerDao? {
        val doc = db.collection("volunteers").document(volunteerEmail).get()
        return doc.get().toObject(VolunteerDao::class.java)
    }

}
