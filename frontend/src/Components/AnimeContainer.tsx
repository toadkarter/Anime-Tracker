import React from "react";
import AnimeEntry from "../types/AnimeEntry";

interface Props {
  date: string,
  anime: AnimeEntry
}

const AnimeContainer = ({date, anime}: Props): JSX.Element => {
  const getEpisodeNumber = () => {
    // @ts-ignore
    return anime.episodesWatched[date]['number']
  }

  return <div>
    <img
      src={anime.imageUrl}
      alt={'anime'}
    />
    <p>{anime.title}</p>
    <p>Episode {getEpisodeNumber()}</p>
  </div>
};

export default AnimeContainer;
