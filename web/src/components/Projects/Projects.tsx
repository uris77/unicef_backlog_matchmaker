import React from 'react';
import { Project } from '../../api/StateSkill';
import { Box, Card, Text } from 'grommet';
import { Github, Html5 } from 'grommet-icons';

interface ProjectItemProps {
  project: Project;
}

const ProjectItem = (props: ProjectItemProps) => {
  const { project } = props;
  const { organization } = project;

  return (
    <Card>
      <Box direction={'column'} gap={'small'} align={'start'} pad={'medium'}>
        <Text weight={'bold'} size={'large'}>
          {project.name}
        </Text>
        <Text size={'medium'}>{organization.name}</Text>
        <Box direction={'row'} gap={'medium'}>
          <Html5
            color={'cadetblue'}
            onClick={() => window.open(organization.website, '_blank')}
          />
          <Github
            onClick={() => {
              window.open(organization.repository, '_blank');
            }}
          />
        </Box>
      </Box>
    </Card>
  );
};

export interface ProjectsProps {
  projects: Project[];
}

const Projects = (props: ProjectsProps) => {
  const { projects } = props;
  return (
    <Box
      gap={'medium'}
      pad={'medium'}
      flex={'grow'}
      background={{ color: 'light-2' }}
    >
      {projects.map((project) => (
        <ProjectItem project={project} />
      ))}
    </Box>
  );
};

export default Projects;
