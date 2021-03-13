import React from 'react';
import { Box, Button } from 'grommet';
import Header from '../../components/Header/Header';
import Skills from '../../components/Skills/Skills';
import { StateSkill } from '../../api/StateSkill';
import skills from '../../models/skills';

const IndexPage = () => {
  const stateSkills: StateSkill[] = skills.map((s) => {
    return {
      skill: s,
      selected: false,
    };
  });
  return (
    <Box>
      <Header />
      <Box
        background={'light-4'}
        height={'large'}
        justify={'start'}
        gap={'small'}
        pad={'small'}
        margin={{
          left: 'small',
          bottom: 'xxsmall',
          right: 'small',
          top: 'small',
        }}
      >
        <Skills skills={stateSkills} />
      </Box>
    </Box>
  );
};

export default IndexPage;
