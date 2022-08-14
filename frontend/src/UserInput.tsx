import React, {Dispatch, SetStateAction, useState} from "react";
import {Button, TextField} from "@mui/material";

interface Props {
  userName: string | undefined;
  setUserName: Dispatch<SetStateAction<string | undefined>>;
}

const UserInput = ({userName, setUserName}: Props): JSX.Element => {
  const [currentInput, setCurrentInput] = useState<string>("")

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setUserName(currentInput);
  }

  return <div>
    <form onSubmit={handleSubmit}>
      <TextField
        id="outlined-basic"
        label="Username"
        variant="outlined"
        defaultValue={userName}
        onChange={(e) => setCurrentInput(e.target.value)}
      />
      <Button
        type={'submit'}
        variant={'outlined'}
        onClick={() => console.log()}
      >
        Submit
      </Button>
    </form>
  </div>
};

export default UserInput;
