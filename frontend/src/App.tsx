import React, {useEffect, useState} from 'react';
import './App.css';
import AnimeEntry from "./types/AnimeEntry";
import AnimeEntries from "./types/AnimeEntries";
import UserInput from "./UserInput";
import animeEntry from "./types/AnimeEntry";
import animeEpisode from "./types/AnimeEpisode";

const App = () => {
  const [selectedUser, setSelectedUser] = useState<string | undefined>()
  const [animeEntries, setAnimeEntries] = useState<AnimeEntries | undefined>(undefined)

  useEffect(() => {
      if (selectedUser) {
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
    }, [selectedUser]);

  const showAnimeIds = () => {
    if (!animeEntries) {
      return;
    }

    return Object.keys(animeEntries).map((animeId) => {
      return <p>{animeId}</p>
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
