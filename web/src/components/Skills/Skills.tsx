import React from 'react';
import { Box, Button, Card, Grid, ResponsiveContext, Text } from 'grommet';
import { Checkmark } from 'grommet-icons';
import { StateSkill, StateSkillProps } from '../../api/StateSkill';
import { useHttpApi } from '../../providers/HttpProvider';
import Spinner from '../Spinner/Spinner';
import { useHistory } from 'react-router-dom';

interface SkillCardProps {
  skill: StateSkill;
  index: number;
  onClick: (s: StateSkill) => any;
}
const SkillCard = (props: SkillCardProps) => {
  const { skill, index, onClick } = props;

  return (
    <Card pad={'medium'} key={index} onClick={(e: any) => onClick(skill)}>
      <Box direction={'row'} gap={'small'} align={'center'}>
        <Text key={index}>{skill.skill}</Text>
        {skill.selected && <Checkmark color={'green'} size={'large'} />}
        {!skill.selected && <Checkmark color={'gray'} size={'large'} />}
      </Box>
    </Card>
  );
};

const Skills = (props: StateSkillProps) => {
  const { skills } = props;
  const size = React.useContext(ResponsiveContext);
  const [stateSkills, setStateSkills] = React.useState<StateSkill[]>(skills);
  const { httpInstance } = useHttpApi();
  const history = useHistory();
  const [search, setSearch] = React.useState<boolean>(false);
  const [enabledSkills, setEnabledSkills] = React.useState<string[]>([]);

  const onSearch = () => {
    // Get all enabled skills
    const enabledSkills = skills
      .filter((skill) => {
        return skill.selected;
      })
      .map((skill) => {
        return skill.skill;
      });
    setEnabledSkills(enabledSkills);
    setSearch(true);
  };

  React.useEffect(() => {
    const updateSkill = () => {
      return httpInstance.post('/updateSkills', enabledSkills);
    };

    const startMatchmaking = () => {
      httpInstance.post('/matchmake', []).then((r) => {
        history.push('/matchedProjects');
      });
    };

    if (search && enabledSkills.length > 0) {
      updateSkill().then((r) => {
        // start the matchmaking engine
        startMatchmaking();
      });
    }
  }, [httpInstance, search, enabledSkills]);

  return (
    <Box
      pad={'medium'}
      background={{ color: 'light-2' }}
      gap={'medium'}
      flex={false}
      direction={'column'}
      justify={'center'}
      margin={{ top: 'small', left: 'large', right: 'large' }}
    >
      {search && <Spinner size={228} />}
      {!search && (
        <Box gap={'medium'}>
          <Grid columns={size !== 'small' ? 'small' : '100%'} gap={'small'}>
            {stateSkills.map((skill, index) => (
              <SkillCard
                skill={skill}
                index={index}
                onClick={(internalState: StateSkill) => {
                  const _skills = stateSkills.map((s) => {
                    if (s.skill === internalState.skill) {
                      s.selected = !s.selected;
                    }
                    return s;
                  });
                  setStateSkills(_skills);
                }}
              />
            ))}
          </Grid>
          <Box flex={false} align={'center'}>
            <Button label={'Search'} onClick={onSearch} />
          </Box>
        </Box>
      )}
    </Box>
  );
};

export default Skills;
