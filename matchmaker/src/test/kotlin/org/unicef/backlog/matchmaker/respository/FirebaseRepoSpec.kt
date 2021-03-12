package org.unicef.backlog.matchmaker.respository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.unicef.backlog.matchmaker.repository.FirebaseRepo

class FirebaseRepoSpec:DescribeSpec( {
    val firebase = FirebaseRepo()
    describe("Firebase Repository") {
        it("should retrieve organizations") {
            val orgs = firebase.fetchOrganizations()
            orgs.size shouldBeGreaterThan 0
        }
        it("should retrieve projects") {
            val projects = firebase.fetchProjects()
            projects.size shouldBeGreaterThan 0
        }

        it("should retrieve project skills") {
            val skills = firebase.fetchProjectSkills()
            skills.size shouldBeGreaterThan 0
        }

        it("should retrieve volunteers") {
            val volunteers = firebase.fetchVolunteers()
            volunteers.size shouldBeExactly 1
        }

        it("should persist volunteer skills") {
            firebase.volunteerSkills.size shouldBeExactly 1
        }
    }
})
