import { Box } from "@mui/material";
import React from "react";
import BookList from "./components/BookList";
import GenrePicker from "./components/GenrePicker";

interface FetchPageState {
  genreId: number | undefined;
}

function FetchPage() {
  const [state, setState] = React.useState<FetchPageState>({
    genreId: undefined,
  });
  const handleGenreChange = (genreId: number | undefined) => {
    setState({ ...state, genreId: genreId });
  };

  return (
    <Box>
      <GenrePicker onGenreChange={handleGenreChange} />
      {state.genreId ? <BookList genreId={state.genreId}></BookList> : null}
    </Box>
  );
}

export default FetchPage;
