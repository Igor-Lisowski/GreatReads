import { Autocomplete, Box, TextField } from "@mui/material";
import { useGetGenresQuery } from "shared/api/genreApi";
import { formatGenres } from "shared/utils/formatGenres";

interface GenrePickerProps {
  onGenreChange: (id: number | undefined) => void;
}

function GenrePicker({ onGenreChange }: GenrePickerProps) {
  const { data, error, isLoading } = useGetGenresQuery();

  return (
    <Box sx={{ width: "100%", display: "flex" }}>
      {error ? (
        <>Oh no, there was an error</>
      ) : isLoading ? (
        <>Loading...</>
      ) : data ? (
        <>
          <Autocomplete
            disablePortal
            options={formatGenres(data)}
            sx={{ width: "100%" }}
            renderInput={(params) => <TextField {...params} label="Genre" />}
            onChange={(_, value) => {
              onGenreChange(value?.genre.id);
            }}
          />
        </>
      ) : null}
    </Box>
  );
}

export default GenrePicker;
