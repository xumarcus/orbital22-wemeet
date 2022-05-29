import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from './App';
import reportWebVitals from './reportWebVitals';
import Features from "./pages/Features";
import Guide from "./pages/Guide";
import About from "./pages/About";
import Wizard from "./pages/Wizard";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<App />} />
                <Route path={"/features"} element={<Features />} />
                <Route path={"/guide"} element={<Guide />} />
                <Route path={"/about"} element={<About />} />
                <Route path={"/wizard"} element={<Wizard />} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
