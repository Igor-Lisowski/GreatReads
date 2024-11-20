import { Box } from "@mui/material";
import BookList from "features/fetch/components/BookList";
import GenrePicker from "features/fetch/components/GenrePicker";
import React from "react";

interface BookListPageState {
  genreId: number | undefined;
}

function BookListPage() {
  const [state, setState] = React.useState<BookListPageState>({
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

export default BookListPage;
