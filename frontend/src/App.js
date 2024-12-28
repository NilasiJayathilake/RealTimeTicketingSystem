import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Navbar from "./Components/Navbar/Navbar";

import HomePage from "./Pages/HomePage/HomePage";
import ViewAnalytics from "./Pages/Simulation/Simulation";
import Simulation from "./Pages/Simulation/Simulation";
import ViewAnalyticsPage from "./Pages/ViewAnalytics/ViewAnalyticsPage";

function App() {
  return (
      <BrowserRouter>
        <div className="App">
          <Navbar/>


          <div id="page-body">
              <Routes>
                  <Route path="/" element={<HomePage/>}/>
                  <Route path="/simulation/:eventId" element={<Simulation/>}/>
                  <Route path="/analytics/:eventId" element={<ViewAnalyticsPage/>}/>

              </Routes>



          </div>
        </div>
      </BrowserRouter>

  );
}

export default App;
