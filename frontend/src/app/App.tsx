import { Box } from "@mui/material";
import BooksPage from "features/browse/BooksPage";
import BookListsPage from "features/fetch/BookListsPage";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
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
            <Route path="/booklists" element={<BookListsPage />} />
          </Routes>
          <Routes>
            <Route path="/books" element={<BooksPage />} />
          </Routes>
        </Box>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
