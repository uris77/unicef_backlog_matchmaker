import React from 'react';
import { Route, Switch } from 'react-router-dom';
import IndexPage from '../pages/Index/IndexPage';
import MatchedProjects from '../pages/MatchedProjects/MatchedProjects';

const Routes = () => {
  return (
    <Switch>
      <Route exact={true} path={'/'} component={IndexPage} />
      <Route
        exact={true}
        path={'/matchedProjects'}
        component={MatchedProjects}
      />
    </Switch>
  );
};

export default Routes;
