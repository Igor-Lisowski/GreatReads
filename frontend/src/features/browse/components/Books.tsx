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
import { useGetBooksByGenreIdQuery } from "shared/api/bookApi";

interface BooksProps {
  genreId: number;
}

function Books({ genreId }: BooksProps) {
  const { data, error, isLoading } = useGetBooksByGenreIdQuery(genreId);

  return (
    <Box>
      {" "}
      {error ? (
        <>Oh no, there was an error</>
      ) : isLoading ? (
        <>Loading...</>
      ) : data ? (
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
      ) : null}
    </Box>
  );
}

export default Books;
