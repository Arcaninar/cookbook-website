import apiConfig from "./apiConfig";
import type { SimpleCookbook } from "../docinterfaces/SimpleCookbook";
import type { Cookbook } from "../docinterfaces/Cookbook";
import type { Rating } from "../docinterfaces/Rating";

function roundRating(cookbook: SimpleCookbook[] | Cookbook) {
  if (Array.isArray(cookbook)) {
    for (const singleCookbook of cookbook) {
      singleCookbook.rating = Math.round(singleCookbook.rating * 10) / 10;
    }
  } else {
    cookbook.rating = Math.round(cookbook.rating * 10) / 10;
  }
}

export async function getAllCookbookData(): Promise<SimpleCookbook[]> {
  try {
    const response = await apiConfig.get("/cookbooks");
    roundRating(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
}

export async function getCookbooksByKeyword(
  keyword: string
): Promise<SimpleCookbook[]> {
  try {
    const response = await apiConfig.get("/cookbooks/search", {
      params: {
        keyword: keyword,
      },
    });
    roundRating(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
}

export async function getCookbookById(id: string): Promise<Cookbook> {
  try {
    const response = await apiConfig.get(`/cookbook/${id}`);
    roundRating(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
}

export async function postNewRating(newRating: Rating): Promise<Rating> {
  try {
    const response = await apiConfig.post("/new/rating", newRating);
    return response.data;
  } catch (error) {
    console.error("Error posting data:", error);
    throw error;
  }
}

export async function modifyExistingRating(
  id: string,
  modifiedRating: Rating
): Promise<void> {
  try {
    await apiConfig.put(`/modify/rating/${id}`, modifiedRating);
  } catch (error) {
    console.error("Error updating data:", error);
    throw error;
  }
}

export async function deleteExistingRating(
  id: string,
  cookbookId: string
): Promise<void> {
  try {
    await apiConfig.delete("/delete/rating", {
      params: {
        id: id,
        cookbookId: cookbookId,
      },
    });
  } catch (error) {
    console.error("Error deleting data:", error);
    throw error;
  }
}
