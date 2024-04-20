import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Genre } from "../types/Genre";
// Define a service using a base URL and expected endpoints
export const genreApi = createApi({
  reducerPath: "genresApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080/",
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  }),
  endpoints: (builder) => ({
    getGenres: builder.query<Genre[], void>({
      query: () => `genre`,
    }),
  }),
});

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useGetGenresQuery } = genreApi;
