package org.unicef.backlog.matchmaker

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import org.kie.api.KieServices
import org.unicef.backlog.matchmaker.domain.*

class RulesSpec: DescribeSpec({

    describe("matches volunteers with projects according to skill") {
        val kServices = KieServices.get()
        val kContainer = kServices.kieClasspathContainer
        val kSession = kContainer.newKieSession("BacklogMatchmaker")

        // Organization setup
        val openCrvs = Organization(
            name="OpenCRVS",
            repository = "https://github.com/opencrvs/opencrvs-core",
            website="https://www.opencrvs.org/")

        val openCrvsProject1 = Project(
            name = "Implement \"Correct Record\" Functionality",
            organization = openCrvs,
        )

        val openCrvsProject1Skills = listOf<ProjectSkill>(
            ProjectSkill(project = openCrvsProject1, skill = "Typescript"),
            ProjectSkill(project = openCrvsProject1, skill = "Mongo"),
            ProjectSkill(project = openCrvsProject1, skill = "Nodejs"),
            ProjectSkill(project = openCrvsProject1, skill = "React"),
            ProjectSkill(project = openCrvsProject1, skill = "Redux"),
            ProjectSkill(project = openCrvsProject1, skill = "Git"),
            ProjectSkill(project = openCrvsProject1, skill = "Graphql"),
        )

        val storyWeaver = Organization(
            name="StoryWeaver",
            repository = "https://github.com/PrathamBooks/sw-core",
            website = "https://storyweaver.org.in/"
        )

        val storyWeaverProject1 = Project(
            name = "SDK for StoryWeaver reader experience",
            organization = storyWeaver
        )
        val storyWeaverProject1Skills = listOf<ProjectSkill>(
            ProjectSkill(project = storyWeaverProject1, skill = "Android"),
            ProjectSkill(project = storyWeaverProject1, skill = "Kotlin"),
            ProjectSkill(project = storyWeaverProject1, skill = "Javascript"),
            ProjectSkill(project = storyWeaverProject1, skill = "React")
        )

        val storyWeaverProject2 = Project(
            name = "Predictive Multilingual search on StoryWeaver",
            organization = storyWeaver
        )
        val storyWeaverProject2Skills = listOf<ProjectSkill>(
            ProjectSkill(project = storyWeaverProject2, skill = "Elasticsearch")
        )

        val curiousLearning = Organization(
            name="Curious Learning",
            repository = "https://github.com/curiouslearning/FeedTheMonster",
            website = "https://www.curiouslearning.org/"
        )

        val curiousLearningProject1 = Project(
            name = "H5P version of Feed the Monster",
            organization =  curiousLearning
        )
        val curiousLearningProject1Skills = listOf<ProjectSkill>(
            ProjectSkill(project = curiousLearningProject1, skill = "Unity"),
            ProjectSkill(project = curiousLearningProject1, skill = "Javascript"),
            ProjectSkill(project = curiousLearningProject1, skill = "React")
        )

        val curiousLearningProject2 = Project(
            name = "H5P version of the Curious Reader authoring tool and rebuilding an existing Read With Akili story as and H5P capable app",
            organization =  curiousLearning
        )
        val curiousLearningProject2Skills = listOf<ProjectSkill>(
            ProjectSkill(project = curiousLearningProject1, skill = "Unity"),
            ProjectSkill(project = curiousLearningProject1, skill = "Javascript"),
            ProjectSkill(project = curiousLearningProject1, skill = "React")
        )


        val primero = Organization(
            name = "Primero",
            repository = "https://github.com/primeroIMS/primero",
            website = "https://www.primero.org/"
        )

        val primeroProject1 = Project(
            name = "Primero Platform integration with RapidPro",
            organization = primero
        )
        val primeroProject1Skills = listOf<ProjectSkill>(
            ProjectSkill(project = primeroProject1, skill = "Rails"),
            ProjectSkill(project = primeroProject1, skill = "Linux")
        )

        val primeroProject2 = Project(
            name = "Develop Primero GDPR compliance tools",
            organization = primero
        )
        val primeroProject2Skills = listOf<ProjectSkill>(
            ProjectSkill(project = primeroProject2, skill = "Rails"),
            ProjectSkill(project = primeroProject2, skill = "Linux")
        )

        val primeroProject3 = Project(
            name = "Primero user customization package",
            organization = primero
        )
        val primeroProject3Skills = listOf<ProjectSkill>(
            ProjectSkill(project = primeroProject3, skill = "Javascript"),
            ProjectSkill(project = primeroProject3, skill = "CSS"),
            ProjectSkill(project = primeroProject3, skill = "UI/UX")
        )

        val primeroProject4 = Project(
            name = "Integrate Rapid Pro Courier Message Broker Module with Primero",
            organization = primero
        )
        val primeroProject4Skills = listOf<ProjectSkill>(
            ProjectSkill(project = primeroProject4, skill = "Javascript"),
            ProjectSkill(project = primeroProject4, skill = "React"),
            ProjectSkill(project = primeroProject4, skill = "Rails"),
            ProjectSkill(project = primeroProject4, skill = "Go"),
            ProjectSkill(project = primeroProject4, skill = "Postgres"),
            ProjectSkill(project = primeroProject4, skill = "Azure")
        )

        // Volunteer Setup

        val volunteer1 = Volunteer(
            email = "volunteer1@mail.com",
            name = "Volunteer First"
        )
        val volunteer1Skills = listOf<VolunteerSkill>(
            VolunteerSkill(volunteer = volunteer1, skill = "Javascript"),
            VolunteerSkill(volunteer = volunteer1, skill = "React")
        )

        val volunteer2 = Volunteer(
            email = "volunteer2@email.com",
            name = "Volunteer Second"
        )

        val volunteer2Skills = listOf<VolunteerSkill>(
            VolunteerSkill(volunteer = volunteer2, skill = "Rails"),
            VolunteerSkill(volunteer = volunteer2, skill = "Postgres")
        )

        val volunteer3 = Volunteer(
            email = "volunteer3@email.com",
            name = "Volunteer Third"
        )
        val volunteer3Skills = listOf<VolunteerSkill>(
            VolunteerSkill(volunteer = volunteer3, skill = "Go"),
            VolunteerSkill(volunteer = volunteer3, skill = "AWS"),
        )

        // Insert Projects and project skills
        kSession.insert(openCrvsProject1)
        openCrvsProject1Skills.forEach { s -> kSession.insert(s)}
        kSession.insert(storyWeaverProject1)
        storyWeaverProject1Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(storyWeaverProject2)
        storyWeaverProject2Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(curiousLearningProject1)
        curiousLearningProject1Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(curiousLearningProject2)
        curiousLearningProject2Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(primeroProject1)
        primeroProject1Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(primeroProject2)
        primeroProject2Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(primeroProject3)
        primeroProject3Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(primeroProject4)
        primeroProject4Skills.forEach{ s -> kSession.insert(s) }

        // Insert volunteers
        kSession.insert(volunteer1)
        volunteer1Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(volunteer2)
        volunteer2Skills.forEach{ s -> kSession.insert(s) }
        kSession.insert(volunteer3)
        volunteer3Skills.forEach{ s -> kSession.insert(s) }

        kSession.fireAllRules()

        it("should find a 5 matches for volunteer 1") {
           kSession.objects.filterIsInstance<VolunteerProjectMatch>().filter{ m ->
                m.volunteer == volunteer1
           }.let{m ->
               m.size shouldBeExactly 5
           }
        }

        it("should find 3 matches for volunteer 2") {
            kSession.objects.filterIsInstance<VolunteerProjectMatch>().filter{ m ->
                m.volunteer == volunteer2
            }.let{m ->
                m.size shouldBeExactly 3
            }
        }

        it("should find 1 match for volunteer 3") {
            kSession.objects.filterIsInstance<VolunteerProjectMatch>().filter{ m ->
                m.volunteer == volunteer3
            }.let{m ->
                m.size shouldBeExactly 1
            }
        }
    }
})
