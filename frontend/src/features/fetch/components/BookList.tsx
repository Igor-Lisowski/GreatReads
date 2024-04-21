import {
  Box,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useGetBookListsByGenreIdQuery } from "../../../shared/api/bookListApi";
import { FetchState } from "../../../shared/types/FetchState";

interface BookListProps {
  genreId: number;
}

function BookList({ genreId }: BookListProps) {
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
                <TableRow>
                  <TableCell>{bookList.name}</TableCell>
                  <TableCell>{bookList.booksNumber}</TableCell>
                  <TableCell>{bookList.votersNumber}</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      disabled={bookList.fetchState !== FetchState.NOT_FETCHED}
                    >
                      {bookList.fetchState === FetchState.NOT_FETCHED
                        ? "Fetch"
                        : bookList.fetchState === FetchState.FETCHED
                        ? "Fetched"
                        : "Fetching"}
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
    </Box>
  );
}

export default BookList;
