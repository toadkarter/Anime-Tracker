import React, {Dispatch, SetStateAction, useState} from "react";
import {Box, Button, TextField, Typography} from "@mui/material";

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
      <Box
        display={'flex'}
        flexDirection={'column'}
        justifyContent={'center'}
        m={2}
      >
        <Box
          display={'flex'}
          flexDirection={'row'}
          justifyContent={'center'}
          flexWrap={'wrap'}
        >
          <TextField
            id="outlined-basic"
            InputLabelProps={{

            }}
            InputProps={{
              inputProps: {
                style: {textAlign: 'center'},
              }
            }}
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
        </Box>
      </Box>
    </form>
  </div>
};

export default UserInput;
