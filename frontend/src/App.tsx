import React from 'react';
import './App.css';
import UserSectorsView from "./components/UserSectorsView";
import {Box} from "@mui/material";

function App() {

  return (
    <Box component="section" sx={{p: 2}} display="flex">
      <UserSectorsView />
    </Box>
  );
}

export default App;
