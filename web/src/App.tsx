
import './App.css';
import {dark, Grommet, Main} from "grommet";
import HttpApiProvider from "./providers/HttpProvider";
import {BrowserRouter} from "react-router-dom";
import Routes from "./components/Routes";

const REACT_APP_API_URL = process.env['REACT_APP_API_URL']
    ? process.env['REACT_APP_API_URL']
    : '';

function App() {
 return (
     <Grommet theme={dark} full>
         <BrowserRouter>
             <Main direction={'column'} flex={false} responsive={true}>
               <HttpApiProvider baseUrl={REACT_APP_API_URL}>
                 <Routes />
               </HttpApiProvider>
             </Main>
         </BrowserRouter>
     </Grommet>
 );
}

export default App;
