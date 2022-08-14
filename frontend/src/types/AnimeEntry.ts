import AnimeEpisode from "./AnimeEpisode";

type AnimeEntry = {
  id: string;
  title: string;
  imageUrl: string;
  startDate: string;
  numEpisodes: number;
  episodesWatched: {[date: string]: AnimeEpisode[]}
}

export default AnimeEntry;