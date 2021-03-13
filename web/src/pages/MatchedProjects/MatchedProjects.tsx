import React from 'react';
import { useHttpApi } from '../../providers/HttpProvider';
import { Project } from '../../api/StateSkill';
import { Box, Text } from 'grommet';
import Spinner from '../../components/Spinner/Spinner';
import Header from '../../components/Header/Header';
import Projects from '../../components/Projects';

interface ProjectData {
  loading: boolean;
  projects: Project[];
  error?: Error;
}

const MatchedProjects = () => {
  const [projectData, setProjectData] = React.useState<ProjectData>({
    loading: true,
    projects: [],
  });
  const { httpInstance } = useHttpApi();

  React.useEffect(() => {
    const fetch = () => {
      httpInstance
        .get('/matched')
        .then((resp) => {
          const projects = resp.data.projects;
          setProjectData({
            loading: false,
            projects,
          });
        })
        .catch((error) => {
          console.error(error);
          setProjectData({
            loading: false,
            projects: [],
            error,
          });
        });
    };

    if (projectData.loading) {
      fetch();
    }
  }, [httpInstance, projectData]);

  return (
    <Box>
      <Header />
      <Box
        pad={'medium'}
        background={{ color: 'light-2' }}
        gap={'medium'}
        flex={false}
        direction={'column'}
        justify={'center'}
        margin={{ top: 'small', left: 'large', right: 'large' }}
      >
        {projectData.loading && <Spinner size={228} />}
        {!projectData.loading && projectData.error && (
          <Box>
            <Text>Ooops. Something didn't go as expected.</Text>
          </Box>
        )}
        {!projectData.loading && !projectData.error && (
          <Box>
            <Projects projects={projectData.projects} />
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default MatchedProjects;
