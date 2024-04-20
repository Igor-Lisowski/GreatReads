import { Genre } from "../types/Genre";
import { formatGenre } from "./formatGenre";

export const formatGenres = (genres: Genre[]) => {
  return genres.map((genre) => formatGenre(genre));
};
