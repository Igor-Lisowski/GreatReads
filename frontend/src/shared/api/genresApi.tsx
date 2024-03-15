import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Genre } from "../types/Genre";

export const genresApi = createApi({
  reducerPath: "genresApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080/" }),
  endpoints: (builder) => ({
    getGenres: builder.query<Genre[], string>({
      query: () => `genres`,
    }),
  }),
});
