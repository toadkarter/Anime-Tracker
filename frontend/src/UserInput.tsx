import {Dispatch, SetStateAction} from "react";
import {Button, TextField} from "@mui/material";

interface Props {
  userName: string;
  setUserName: Dispatch<SetStateAction<string>>;
}


// Animation Bug Fix Credit:
// https://stackoverflow.com/questions/55647969/how-to-get-one-elements-to-slide-in-while-the-other-slides-out-using-react-and

const UserInput = ({userName, setUserName}: Props): JSX.Element => {
  return <div>
    <TextField
      id="outlined-basic"
      label="Outlined"
      variant="outlined"
      defaultValue={userName}
    />
    <Button
      onClick={() => setUserName}
    >

    </Button>

  </div>
};

export default UserInput;
