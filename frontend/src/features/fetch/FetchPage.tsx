import { Box } from "@mui/material";
import BookList from "features/fetch/components/BookList";
import GenrePicker from "features/fetch/components/GenrePicker";
import React from "react";

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
