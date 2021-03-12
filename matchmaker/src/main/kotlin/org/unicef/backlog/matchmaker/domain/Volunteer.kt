package org.unicef.backlog.matchmaker.domain

data class Volunteer(
    val email: String,
    val name: String,
)

data class VolunteerSkill(
    val volunteer: Volunteer,
    val skill: String,
)


data class VolunteerProjectMatch(
    val volunteer: Volunteer,
    val project: Project,
)
