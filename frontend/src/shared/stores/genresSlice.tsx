import { createSlice } from "@reduxjs/toolkit";
import { Genre } from "../types/Genre";

// Define a type for the slice state
interface GenresState {
  genres: Array<Genre>;
}

// Define the initial state using that type
const initialState: GenresState = {
  genres: [],
};

export const genresSlice = createSlice({
  name: "genres",
  initialState: initialState,
  reducers: {
    set: (state, action) => {
      state.genres = action.payload;
    },
  },
});

export const { set } = genresSlice.actions;

export const selectGenres = (state: GenresState) => state.genres;

export default genresSlice.reducer;
