import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";

function NavigationBar() {
  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ mr: 2 }}>
            GreatReads
          </Typography>
          <Box
            sx={{
              flexGrow: 1,
              display: "flex",
            }}
          >
            <Button
              href="/booklists"
              sx={{ color: "white", display: "block", mx: 2, paddingBottom: 0 }}
            >
              BookLists
            </Button>
            <Button
              href="/books"
              sx={{ color: "white", display: "block", mx: 2, paddingBottom: 0 }}
            >
              Books
            </Button>
            <Button
              sx={{ color: "white", display: "block", mx: 2, paddingBottom: 0 }}
            >
              Jobs
            </Button>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default NavigationBar;
