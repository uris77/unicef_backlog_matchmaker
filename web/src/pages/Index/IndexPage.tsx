import React from 'react';
import {Box} from "grommet";
import Header from "../../components/Header/Header";

const IndexPage = () => {
    return (
        <Box>
            <Header />
            <Box
                background={'light-1'}
                height={'large'}
                align={'center'}
                justify={'start'}
                gap={'large'}
                pad={'small'}
                margin={{
                    left: 'small',
                    bottom: 'xxsmall',
                    right: 'small',
                    top: 'small',
                }}
            >
            </Box>
        </Box>
    );
}

export default IndexPage;
