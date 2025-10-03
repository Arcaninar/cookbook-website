import { useEffect, useState } from "react";
import "../css/CookbookDetail.css";

import type { Cookbook } from "../docinterfaces/Cookbook";
import type { Rating } from "../docinterfaces/Rating";
import { getCookbookById, postNewRating } from "../services/api";

interface CookbookDetailProps {
  cookbookId: string;
  onBack: () => void;
}

function CookbookDetail({ cookbookId, onBack }: CookbookDetailProps) {
  const [cookbook, setCookbook] = useState<null | Cookbook>(null);
  const [ratings, setRatings] = useState<Rating[]>([]);
  const [newReview, setNewReview] = useState("");
  const [newRating, setNewRating] = useState(0);

  useEffect(() => {
    fetchData();
  }, [cookbookId]);

  const fetchData = async () => {
    const cookbook = await getCookbookById(cookbookId);
    setCookbook(cookbook);
    setRatings(cookbook.ratingList);
    console.log(cookbook);
    console.log(ratings);
  };

  const handleCommentSubmit = async () => {
    console.log(cookbookId);
    const rating: Rating = {
      id: null,
      cookbookId: cookbookId,
      rating: newRating,
      review: newReview,
    };
    await postNewRating(rating);
    setNewReview("");
    setNewRating(0);

    const newAddedRatingList = [rating, ...ratings];
    setRatings(newAddedRatingList);
  };

  if (cookbook === null) {
    return (
      <div className="loading-animation-detail">
        <div className="spinner"></div>
        <p>Loading...</p>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="cookbook-detail">
        <div className="header">
          <h2 className="cookbook-title">{cookbook.name}</h2>
          <button className="back-button" onClick={onBack}>
            X
          </button>
        </div>
        <img
          src={cookbook.image}
          alt={cookbook.name}
          className="cookbook-image"
        />
        <p className="cookbook-category">{cookbook.category}</p>
        <p className="cookbook-time">
          Cooking Time: {cookbook.cookingTime} Minutes
        </p>
        <div className="rating">Rating: {cookbook.rating}/5</div>

        <div className="comments-section">
          <h3>Comments</h3>
          <div className="comments-list">
            {ratings.map((rating) => (
              <div key={rating.cookbookId}>
                <p>
                  Rating: {rating.rating}/5 - {rating.review}
                </p>
              </div>
            ))}
          </div>
          <h4>Leave a Rating</h4>
          <div className="star-rating">
            {[1, 2, 3, 4, 5].map((star) => (
              <span
                key={star}
                className={`star ${newRating >= star ? "filled" : ""}`}
                onClick={() => setNewRating(star)}
              >
                ★
              </span>
            ))}
          </div>
          <textarea
            value={newReview}
            onChange={(e) => setNewReview(e.target.value)}
            placeholder="Your comment"
          />
          <div className="button-container">
            <button onClick={handleCommentSubmit}>Submit</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CookbookDetail;
