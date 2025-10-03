import "./css/App.css";
import { useState, useEffect } from "react";
import Home from "./pages/Home";
import CookbookDetail from "./pages/CookbookDetail";

import type { SimpleCookbook } from "./docinterfaces/SimpleCookbook";
import { getAllCookbookData } from "./services/api";

function App() {
  const [allCookbooks, setAllCookbooks] = useState<null | SimpleCookbook[]>(
    null
  );
  const [selectedCookbook, setSelectedCookbook] =
    useState<null | SimpleCookbook>(null);

  useEffect(() => {
    fetchCookbooks();
  }, []);

  const fetchCookbooks = async () => {
    const cookbooks = await getAllCookbookData();
    setAllCookbooks(cookbooks);
  };

  return (
    <div className="App">
      {selectedCookbook ? (
        <CookbookDetail
          cookbookId={selectedCookbook.id}
          onBack={() => setSelectedCookbook(null)}
        />
      ) : (
        <Home
          allCookbooks={allCookbooks}
          setAllCookbooks={setAllCookbooks}
          onSelectCookbook={setSelectedCookbook}
        />
      )}
    </div>
  );
}

export default App;
