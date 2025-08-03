const handleJsonResponse = async <T>(res: Response, defaultMessage: string): Promise<T> => {
  if (!res.ok) {
    const errorJson = await res.json().catch(() => null);
    const message = errorJson?.message || defaultMessage;
    throw new Error(message);
  }
  return res.json();
};

export { handleJsonResponse };
