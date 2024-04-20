import { Genre } from "../types/Genre";

export const formatGenre = (genre: Genre) => {
  let label = genre.label;
  if (label.indexOf("-") === label.length - 2) {
    label = label.replace("-", "'");
  } else if (label.indexOf("-") > -1) {
    label = label.replace("-", " ");
  }

  label = label.substring(0, 1).toUpperCase() + label.substring(1);
  var spaceIndex = label.indexOf(" ");
  if (spaceIndex > -1) {
    label =
      label.substring(0, spaceIndex + 1) +
      label.substring(spaceIndex + 1, spaceIndex + 2).toUpperCase() +
      label.substring(spaceIndex + 2);
  }
  return { genre, label: label };
};
