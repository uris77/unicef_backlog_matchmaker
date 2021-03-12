package org.unicef.backlog.matchmaker.dao

import org.unicef.backlog.matchmaker.domain.*

data class InitializeDataInput(
    val organizations: List<Organization> = emptyList(),
    val projects: List<Project> = emptyList(),
    val projectSkills: List<ProjectSkill> = emptyList(),
    val volunteers: List<Volunteer> = emptyList(),
    val volunteerSkills: List<VolunteerSkill> = emptyList(),
)
