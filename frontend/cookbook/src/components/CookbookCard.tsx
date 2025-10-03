import "../css/CookbookCard.css";

import type { SimpleCookbook } from "../docinterfaces/SimpleCookbook";

interface CookbookCardProps {
  simpleCookbook: SimpleCookbook;
  onClick: () => void;
}

function CookbookCard({ simpleCookbook, onClick }: CookbookCardProps) {
  return (
    <div className="card" onClick={onClick}>
      <img
        src={simpleCookbook.image}
        alt={simpleCookbook.name}
        className="card-image"
      />
      <div className="card-body">
        <div className="card-title-wrapper">
          <h3 className="card-title">{simpleCookbook.name}</h3>
        </div>
        <p className="card-category">{simpleCookbook.category}</p>
        <p className="card-time">
          Cooking Time: {simpleCookbook.cookingTime} Minutes
        </p>
        <div className="rating">Rating: {simpleCookbook.rating}/5</div>
      </div>
    </div>
  );
}

export default CookbookCard;
