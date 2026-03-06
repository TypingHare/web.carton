package command

import (
	"fmt"
	"slices"
	"sort"

	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func ListCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "list",
		Short: "Show all web records",
		RunE: func(cmd *cobra.Command, args []string) error {
			cabinet, err := share.GetWebCabinet(d)
			if err != nil {
				return fmt.Errorf("failed to get web cabinet: %w", err)
			}

			webRecords := slices.Clone(cabinet.Objects)
			sort.Slice(webRecords, func(i, j int) bool {
				return webRecords[i].UpdatedAt > webRecords[j].UpdatedAt
			})

			for _, webRecord := range webRecords {
				cmd.Printf("%s  %s\n", webRecord.Name, webRecord.Url)
			}

			return nil
		},
	}

	return command
}
