package org.unicef.backlog.matchmaker.engine

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.unicef.backlog.matchmaker.dao.InitializeDataInput
import org.unicef.backlog.matchmaker.domain.*


class EngineInstance(private val kieContainer: KieContainer, private val initializeDataInput: InitializeDataInput) {
//    private val RULE = "classpath*:/org/unicef/backlog/matchmaker/rules.drl"
//    private val kieServices = KieServices.Factory.get()
//    private val kFileSystem = kieServices.newKieFileSystem()
//    private val kBuilder = kieServices.newKieBuilder(kFileSystem)
    private var kieSession: KieSession = kieContainer.newKieSession("BacklogMatchmaker")

    init {
        insertOrganizations(initializeDataInput.organizations)
        insertProjects(initializeDataInput.projects)
        insertProjectSkills(initializeDataInput.projectSkills)
        insertVolunteer(initializeDataInput.volunteers)
        insertVolunteerSkills(initializeDataInput.volunteerSkills)
        kieSession.fireAllRules()
    }

    private fun insertOrganizations(orgs: Collection<Organization>) {
        orgs.forEach{org ->
            kieSession.insert(org)
        }
    }

    private fun insertProjects(projects: Collection<Project>) {
        projects.forEach{ project ->
            kieSession.insert(project)
        }
    }

    private fun insertProjectSkills(skills: Collection<ProjectSkill>){
        skills.forEach{ skill -> kieSession.insert(skill) }
    }

    private fun insertVolunteer(volunteers: Collection<Volunteer>) {
        volunteers.forEach{ volunteer -> kieSession.insert(volunteer)}
    }

    private fun insertVolunteerSkills(skills: Collection<VolunteerSkill>) {
        skills.forEach{skill -> kieSession.insert(skill)}
    }

    fun matchedSkills(): List<VolunteerProjectMatch> {
        return kieSession.objects.filterIsInstance<VolunteerProjectMatch>()
    }

    fun dispose() {
        kieSession.dispose()
    }
}
