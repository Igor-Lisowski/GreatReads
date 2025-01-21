import { Box } from "@mui/material";
import BookLists from "features/fetch/components/BookLists";
import React from "react";
import GenrePicker from "shared/components/GenrePicker";

interface BookListPageState {
  genreId: number | undefined;
}

function BookListsPage() {
  const [state, setState] = React.useState<BookListPageState>({
    genreId: undefined,
  });
  const handleGenreChange = (genreId: number | undefined) => {
    setState({ ...state, genreId: genreId });
  };

  return (
    <Box>
      <GenrePicker onGenreChange={handleGenreChange} />
      {state.genreId ? <BookLists genreId={state.genreId}></BookLists> : null}
    </Box>
  );
}

export default BookListsPage;
