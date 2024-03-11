import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";

function NavigationBar() {
  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ mr: 5 }}>
            GreatReads
          </Typography>
          <Box
            sx={{
              flexGrow: 1,
              display: "flex",
            }}
          >
            <Button
              sx={{ color: "white", display: "block", mx: 3, paddingBottom: 0 }}
            >
              Fetch
            </Button>
            <Button
              sx={{ color: "white", display: "block", mx: 3, paddingBottom: 0 }}
            >
              Browse
            </Button>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default NavigationBar;
