const common = {
  reducers: {
    updateState(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};

export default common