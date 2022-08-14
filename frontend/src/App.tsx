import React, {useEffect, useState} from 'react';
import './App.css';
import AnimeEntry from "./types/AnimeEntry";
import AnimeEntries from "./types/AnimeEntries";
import UserInput from "./Components/UserInput";
import Timetable from "./types/Timetable";
import DayContainer from "./Components/DayContainer";
import {Box} from "@mui/material";

const App = () => {
  const [selectedUser, setSelectedUser] = useState<string | undefined>();
  const [animeEntries, setAnimeEntries] = useState<AnimeEntries | undefined>(undefined);
  const [timetable, setTimetable] = useState<Timetable | undefined>(undefined);

  useEffect(() => {
      const fetchSelectedUser = () => {
        fetch(`http://127.0.0.1:8080/user?user_name=${selectedUser}`)
          .then((response) => {
            if (response.ok) {
              return response.json() as Promise<AnimeEntries>;
            } else {
              throw new Error();
            }
          })
          .then((data) => {
            setAnimeEntries(data);
          })
          .catch((error) => console.log(error));
      }

      const fetchTimetable = () => {
        fetch("http://127.0.0.1:8080/episodes")
          .then((response) => {
            if (response.ok) {
              return response.json() as Promise<Timetable>;
            } else {
              throw new Error();
            }
          })
          .then((data) => {
            setTimetable(data);
          })
          .catch((error) => console.log(error));
      }

      if (selectedUser) {
        fetchSelectedUser();
        fetchTimetable();
      }
    }, [selectedUser]);

  const showAnimeIds = () => {
    if (!animeEntries || !timetable) {
      return;
    }

    return Object.keys(timetable).map((val, index, array) => array[array.length - 1 - index])
      .map((date) => {
        const currentAnimeEntries: AnimeEntry[] = timetable[date].map(id => animeEntries[id])
        return <>
          <DayContainer date={date} animeEntries={currentAnimeEntries}/>
        </>
      })
  }

  return (
    <div className="App">
      <UserInput userName={selectedUser} setUserName={setSelectedUser}/>
        {showAnimeIds()}
    </div>
  );
}
export default App;
