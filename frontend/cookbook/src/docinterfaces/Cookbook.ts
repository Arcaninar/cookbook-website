import type { NutritionalFacts } from "./NutritionalFacts";
import type { Rating } from "./Rating";

export interface Cookbook {
  id: string;
  name: string;
  category: string;
  cookingTime: number;
  ingredients: string[];
  labels: string[];
  rating: number;
  image: string;
  tools: string[];
  steps: string[];
  nutritionalFacts: NutritionalFacts;
  ratingCount?: number;
  ratingList: Rating[];
}
