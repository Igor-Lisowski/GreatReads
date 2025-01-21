import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import BookList from "features/fetch/components/BookList";
import { useGetBookListsByGenreIdQuery } from "shared/api/bookListApi";

interface BookListProps {
  genreId: number;
}

function BookLists({ genreId }: BookListProps) {
  const { data, error, isLoading } = useGetBookListsByGenreIdQuery(genreId);

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
                <TableCell>Books Number</TableCell>
                <TableCell>Voters Number</TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data.map((bookList) => (
                <BookList key={bookList.id} bookList={bookList}></BookList>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
    </Box>
  );
}

export default BookLists;
