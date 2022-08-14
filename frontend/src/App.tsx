import React, {useEffect, useState} from 'react';
import './App.css';
import AnimeEntry from "./types/AnimeEntry";
import AnimeEntries from "./types/AnimeEntries";

const App = () => {
  const [animeEntries, setAnimeEntries] = useState<AnimeEntries[]>([])

  useEffect(() => {
      fetch('http://127.0.0.1:8080/user?user_name=toadkarter1993')
        .then((response) => {
          if (response.ok) {
            return response.json() as Promise<AnimeEntries[]>;
          } else {
            throw new Error();
          }
        })
        .then((data) => {
          setAnimeEntries(data);
        })
        .catch((error) => console.log(error));
    }, []);


  return (
    <div className="App">
    </div>
  );
}

export default App;
