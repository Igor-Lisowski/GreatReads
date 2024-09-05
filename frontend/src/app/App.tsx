import { Box } from "@mui/material";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import FetchPage from "../features/fetch/FetchPage";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import store from "./store";

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <NavigationBar />
        <Box sx={{ margin: "20px 10vw" }}>
          <Routes>
            <Route path="/fetch" element={<FetchPage />} />
          </Routes>
        </Box>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
