import React from "react";
import AnimeEntry from "../types/AnimeEntry";
import AnimeContainer from "./AnimeContainer";
import {Box} from "@mui/material";

interface Props {
  date: string,
  animeEntries: AnimeEntry[],
}

const DayContainer = ({date, animeEntries}: Props): JSX.Element => {

  return <div>
    <p>Today's Date is {date}</p>
    <Box
      display={'flex'}
      flexDirection={'row'}
      flexWrap={'wrap'}
      justifyContent={'center'}
      p={1}
    >
      {animeEntries.map(animeEntry => {
        return <AnimeContainer date={date} anime={animeEntry}/>
      })}
    </Box>
  </div>
};

export default DayContainer;
