import { useState } from "react";
import CookbookCard from "../components/CookbookCard";
import "../css/Home.css";

import type { SimpleCookbook } from "../docinterfaces/SimpleCookbook";
import { getCookbooksByKeyword } from "../services/api";

interface HomeProps {
  allCookbooks: SimpleCookbook[] | null;
  setAllCookbooks: (allCookbooks: SimpleCookbook[]) => void;
  onSelectCookbook: (cookbook: SimpleCookbook) => void;
}

function Home({ allCookbooks, setAllCookbooks, onSelectCookbook }: HomeProps) {
  const [searchTerm, setSearchTerm] = useState("");

  const handleSearch = async () => {
    const cookbooks = await getCookbooksByKeyword(searchTerm);
    setAllCookbooks(cookbooks);
  };

  return (
    <div className="container">
      <div className="hero-banner">
        <h1>Welcome to Cookbook Finder</h1>
        <p>Explore delicious cookbooks and share your thoughts!</p>
      </div>
      <div className="search-bar">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Search for cookbooks..."
        />
        <button onClick={handleSearch}>🔍</button>
      </div>
      {allCookbooks ? (
        <div className="card-grid">
          {allCookbooks.map((cookbook) => (
            <CookbookCard
              key={cookbook.id}
              simpleCookbook={cookbook}
              onClick={() => onSelectCookbook(cookbook)}
            />
          ))}
        </div>
      ) : (
        <div className="loading-animation-home">
          <div className="spinner"></div>
          <p className="loading">Loading...</p>
        </div>
      )}
    </div>
  );
}

export default Home;
