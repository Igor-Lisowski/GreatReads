import { Box } from "@mui/material";
import Books from "features/browse/components/Books";
import React from "react";
import GenrePicker from "shared/components/GenrePicker";

interface BooksState {
  genreId: number | undefined;
}

function BooksPage() {
  const [state, setState] = React.useState<BooksState>({
    genreId: undefined,
  });
  const handleGenreChange = (genreId: number | undefined) => {
    setState({ ...state, genreId: genreId });
  };

  return (
    <Box>
      <GenrePicker onGenreChange={handleGenreChange} />
      {state.genreId ? <Books genreId={state.genreId}></Books> : null}
    </Box>
  );
}

export default BooksPage;
