import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import Book from "features/browse/components/Book";
import BooksPagination from "features/browse/components/BooksPagination";
import { useEffect, useState } from "react";
import { useGetBooksByGenreIdQuery } from "shared/api/bookApi";

interface BooksProps {
  genreId: number;
}

function Books({ genreId }: BooksProps) {
  const updatePageNumber = (pageNumber: number) => {
    setPageNumber(pageNumber);
  };

  const [pageNumber, setPageNumber] = useState<number>(1);

  const { data, error, isLoading, refetch } = useGetBooksByGenreIdQuery({
    genreId: genreId,
    pageNumber: pageNumber,
  });

  useEffect(() => {
    refetch();
  }, [pageNumber, refetch]);

  return (
    <Box>
      {" "}
      {error ? (
        <>Oh no, there was an error</>
      ) : isLoading ? (
        <>Loading...</>
      ) : data ? (
        <Box>
          <TableContainer style={{ maxHeight: "75vh" }}>
            <Table stickyHeader>
              <TableHead>
                <TableRow>
                  <TableCell>Name</TableCell>
                  <TableCell>Rating</TableCell>
                  <TableCell>Author</TableCell>
                  <TableCell>Ratings Number</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {data.map((book) => (
                  <Book key={book.id} book={book}></Book>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <BooksPagination
            genreId={genreId}
            updatePageNumber={updatePageNumber}
          />
        </Box>
      ) : null}
    </Box>
  );
}

export default Books;
