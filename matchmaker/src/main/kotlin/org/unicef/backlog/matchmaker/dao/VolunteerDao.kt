package org.unicef.backlog.matchmaker.dao

import org.unicef.backlog.matchmaker.domain.Project

data class VolunteerDao(val email: String = "", val name: String = "", val skills: List<String> = emptyList(), val projects: List<Project> = emptyList())
