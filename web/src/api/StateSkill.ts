export interface StateSkill {
  skill: string;
  selected: boolean;
}

export interface StateSkillProps {
  skills: StateSkill[];
}

export interface Organization {
  name: string;
  repository: string;
  website: string;
}

export interface Project {
  name: string;
  organization: Organization;
}
