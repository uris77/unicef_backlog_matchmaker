package org.unicef.backlog.matchmaker.domain

data class Organization(
    val name: String = "",
    val repository: String = "",
    val website: String = "",
)


data class Project(
    val name: String = "",
    val organization: Organization = Organization(),
)

data class ProjectSkill(
    val project: Project,
    val skill: String,
)

// Categories can be
// Open Source Software
// Open Data
// Open AI Model
// Open Standard
// Open Content
data class ProjectCategory(
    val organization: Organization,
    val category: String,
)

// Sustainable Development Goals can be
// End Poverty in all its forms everywhere
// Zero Hunger
// Good Health & Well-Being
// Quality Education
// Gender Equity
// Clean Water & Sanitation
// Affordable & Clean Energy
// Decent Work & Economic Growth
// Industry, Innovation & Infrastructure
// Reduced Inequalities
// Sustainable Cities & Communities
// Responsible Consumption & Production
// Climate Action
// Life Below Water
// Live on Land
// Peace, Justice & Strong Institutions
// Partnerships for the Goals
data class ProjectSdg(
    val organization: Organization,
    val sdg: String
)
