import React from 'react';
import { Route, Switch } from 'react-router-dom';
import IndexPage from "../pages/Index/IndexPage";

const Routes = () => {
    return (
        <Switch>
            <Route exact={true} path={'/'} component={IndexPage} />
        </Switch>
    )
};

export default Routes;
